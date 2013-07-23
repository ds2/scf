/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2013  Dirk Strauss
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.googlecode.socofo.runtime.impl;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.common.api.MainRuntime;
import com.googlecode.socofo.common.impl.IOHelperImpl;
import com.googlecode.socofo.core.api.FileDestination;
import com.googlecode.socofo.core.api.FileRoot;
import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.Scheduler;
import com.googlecode.socofo.core.api.SourceTransformer;
import com.googlecode.socofo.core.api.SourceTypeDetector;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.core.api.SourcefileScanner;
import com.googlecode.socofo.core.api.TransformerDelegate;
import com.googlecode.socofo.core.api.TranslationJob;
import com.googlecode.socofo.core.impl.LineHandlerImpl;
import com.googlecode.socofo.core.impl.SchedulerImpl;
import com.googlecode.socofo.core.impl.SourceWriterImpl;
import com.googlecode.socofo.core.impl.TransformerDelegateImpl;
import com.googlecode.socofo.core.impl.TypeDetectorImpl;
import com.googlecode.socofo.core.impl.io.SourcefileScannerImpl;
import com.googlecode.socofo.core.impl.xml.TreeHandler;
import com.googlecode.socofo.core.impl.xml.TreeHandlerImpl;
import com.googlecode.socofo.core.impl.xml.XmlTransformer;
import com.googlecode.socofo.core.provider.FileDestinationImpl;
import com.googlecode.socofo.core.provider.FileRootImpl;
import com.googlecode.socofo.core.provider.TranslationJobImpl;
import com.googlecode.socofo.rules.api.RulesConfig;
import com.googlecode.socofo.rules.api.v1.RulesLoader;
import com.googlecode.socofo.rules.impl.RulesLoaderImpl;

/**
 * The injection plan for the runtime.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class RuntimeInjectionPlan implements Module {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(final Binder binder) {
        binder.bind(MainRuntime.class).to(DefRuntime.class);
        binder.bind(Scheduler.class).to(SchedulerImpl.class);
        binder.bind(FileDestination.class).to(FileDestinationImpl.class);
        binder.bind(FileRoot.class).to(FileRootImpl.class);
        binder.bind(RulesLoader.class).to(RulesLoaderImpl.class);
        binder.bind(SourcefileScanner.class).to(SourcefileScannerImpl.class);
        binder.bind(TranslationJob.class).to(TranslationJobImpl.class);
        binder.bind(SourceTypeDetector.class).to(TypeDetectorImpl.class);
        binder.bind(IOHelper.class).to(IOHelperImpl.class);
        binder.bind(TransformerDelegate.class).to(TransformerDelegateImpl.class);
        binder.bind(RulesConfig.class).to(RulesConfigLiveImpl.class);
        binder.bind(SourceTransformer.class).annotatedWith(Names.named("xml")).to(XmlTransformer.class);
        binder.bind(LineHandler.class).to(LineHandlerImpl.class);
        binder.bind(SourceWriter.class).to(SourceWriterImpl.class);
        binder.bind(TreeHandler.class).to(TreeHandlerImpl.class);
    }
    
}
