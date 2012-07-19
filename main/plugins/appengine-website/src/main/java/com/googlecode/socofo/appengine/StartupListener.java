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
package com.googlecode.socofo.appengine;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kaeto23
 * 
 */
public class StartupListener implements ServletContextListener {
	/**
	 * A logger.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(StartupListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LOG.info("Context destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LOG.info("Context initialized");
	}

}
