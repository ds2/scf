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
/**
 * 
 */
package com.googlecode.socofo.core.impl.xml;

/**
 * A write state. Will be discontinued.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public enum WriteState {
	/**
	 * undefined write state. Usually, do not write NOW to the destination
	 * stream.
	 */
	UNDEFINED,
	/**
	 * The element must be written now.
	 */
	MustWriteElement;
}
