/*
 * Licensed to the Technische Universität Darmstadt under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Technische Universität Darmstadt 
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.
 *  
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dkpro.script.groovy

import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.*
import static org.apache.uima.fit.pipeline.SimplePipeline.*

import org.apache.uima.analysis_engine.AnalysisEngineDescription
import org.apache.uima.collection.CollectionReaderDescription
import org.dkpro.script.groovy.engine.PipelineEngine
import org.dkpro.script.groovy.engine.SimplePipelineEngine
import org.dkpro.script.groovy.internal.PipelineContext
import org.dkpro.script.groovy.internal.PipelineHelper

abstract class DKProCoreScript extends DelegatingScript {
    abstract void scriptBody()

    def run() {
        // Avoid an NPE while calling context.findClassLoader() which calls back into this class
        setDelegate(this)

        PipelineContext context = new PipelineContext()
        context.scriptContext = this
        context.classLoader = this.class.classLoader
        
        // We set the thread context classloader such that UIMA/uimaFIT has access to all the 
        // classes defined in the script and loaded via grab.
        ClassLoader oldLoader = Thread.currentThread().getContextClassLoader()
        Thread.currentThread().setContextClassLoader(context.findClassLoader())
        try {
            context.boot()
            
            // Now set the true DSL delegate
            PipelineHelper pipelineHelper = new PipelineHelper(context)
            setDelegate(pipelineHelper)
            
            scriptBody()
            
            if (!context.pipeline.empty) {
                PipelineEngine engine = new SimplePipelineEngine();
                engine.execute(context);
            }
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldLoader)
        }
    }
}