/**
 * SoCoFo Source Code Formatter
 * Copyright (C) 2009 Dirk Strauss <lexxy23@gmail.com>
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
/**
 * 
 */
package com.googlecode.socofo.core.impl.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.googlecode.socofo.core.api.FileRoot;
import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceTransformer;
import com.googlecode.socofo.core.api.SourceTypeDetector;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.core.api.StreamRoot;
import com.googlecode.socofo.core.impl.LineHandlerImpl;
import com.googlecode.socofo.core.impl.SourceWriterImpl;
import com.googlecode.socofo.core.impl.TypeDetectorImpl;
import com.googlecode.socofo.core.impl.io.FileRootImpl;
import com.googlecode.socofo.core.impl.io.StreamRootImpl;
import com.googlecode.socofo.core.impl.xml.XmlTransformer;


/**
 * @author kaeto23
 * 
 */
public class CoreInjectionPlan implements Module {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(Binder binder) {
		binder.bind(SourceTypeDetector.class).to(TypeDetectorImpl.class);
		binder.bind(SourceTransformer.class).annotatedWith(Names.named("xml"))
				.to(XmlTransformer.class);
		binder.bind(SourceWriter.class).to(SourceWriterImpl.class);
		binder.bind(StreamRoot.class).to(StreamRootImpl.class);
		binder.bind(FileRoot.class).to(FileRootImpl.class);
		binder.bindConstant().annotatedWith(Names.named("defaultEncoding")).to(
				"utf-8");
		binder.bind(LineHandler.class).to(LineHandlerImpl.class);
	}

}
