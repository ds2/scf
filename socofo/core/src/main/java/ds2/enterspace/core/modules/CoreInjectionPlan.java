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
