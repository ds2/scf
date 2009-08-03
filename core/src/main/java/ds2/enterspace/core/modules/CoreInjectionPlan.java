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
package ds2.enterspace.core.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

import ds2.enterspace.core.api.FileRoot;
import ds2.enterspace.core.api.SourceTransformer;
import ds2.enterspace.core.api.SourceTypeDetector;
import ds2.enterspace.core.api.SourceWriter;
import ds2.enterspace.core.api.StreamRoot;
import ds2.enterspace.core.impl.SourceWriterImpl;
import ds2.enterspace.core.impl.TypeDetectorImpl;
import ds2.enterspace.core.impl.io.FileRootImpl;
import ds2.enterspace.core.impl.io.StreamRootImpl;
import ds2.enterspace.core.impl.xml.XmlTransformer;

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
	}

}
