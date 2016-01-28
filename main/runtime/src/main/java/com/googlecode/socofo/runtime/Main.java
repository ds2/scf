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
package com.googlecode.socofo.runtime;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.socofo.common.api.MainRuntime;
import com.googlecode.socofo.runtime.impl.RuntimeInjectionPlan;
import org.slf4j.*;

import java.lang.invoke.MethodHandles;

/**
 * Main program to let the formatter run on in a terminal.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public final class Main {
    private static final Logger LOG= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * The main method.
     * 
     * @param args
     *            the runtime arguments.
     */
    public static void main(final String[] args) {
        LOG.debug("Starting injector..");
        final Injector ij = Guice.createInjector(new RuntimeInjectionPlan());
        LOG.debug("Getting main runtime..");
        final MainRuntime mr = ij.getInstance(MainRuntime.class);
        LOG.debug("Starting parse of parameters: {}", args);
        mr.parseParams(args);
        LOG.debug("Alright, we start");
        final int rc = mr.execute();
        LOG.debug("done with execution. Using return code {}", rc);
        System.exit(rc);
    }
    
    /**
     * Would init the class.
     */
    private Main() {
        // nothing to do
    }
    
}
