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
package org.dkpro.script.groovy.engine;

import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.forceTypeDescriptorsScan;
import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.dkpro.script.groovy.internal.PipelineContext;

class SimplePipelineEngine implements PipelineEngine 
{
    public void execute(PipelineContext aContext)
    {
        // Force re-scan of type systems because we dynamically add JARs to the
        // classpath using grape - failure to do so will cause some types not to
        // be detected when the pipeline is actually run
        forceTypeDescriptorsScan()
        def ts = createTypeSystemDescription()
        
        // runpipeline constructs the type system from the descriptors passed to
        // it - make sure at least one of the components actually has the full
        // type system
        aContext.pipeline[0].desc.collectionReaderMetaData.typeSystem = ts
        runPipeline(
            aContext.pipeline[0].desc as CollectionReaderDescription,
            aContext.pipeline[1..-1].collect { it.desc } as AnalysisEngineDescription[])
    }
}
