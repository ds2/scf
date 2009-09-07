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
package com.googlecode.socofo.core.api;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * The scheduler. Implementations of this class are scheduling and
 * starting/stopping translation jobs.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface Scheduler {

	public void setRules(URL formatterXml);

	/**
	 * Starts the scheduler. The scheduler will create a separate thread group
	 * and executes the jobs in there.
	 */
	public void startScheduler();

	public void addWaiterThreads(Thread currentThread);

	public List<String> getErrorMessages();

	public List<TranslationJob> createLocalJobs(File baseDir, File targetDir,
			SourceTypes... types);

	public void addJobs(List<TranslationJob> jobs);

	public int getActiveJobsCount();
}
