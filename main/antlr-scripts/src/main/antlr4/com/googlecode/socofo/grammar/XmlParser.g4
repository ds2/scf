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
parser  grammar XmlParser;

options {
tokenVocab=XmlGrammar; 
language=Java;
}

document  : element ;

element
    : startTag
        (element
        | PCDATA
        )*
        endTag
    | emptyElement
    ;

startTag  : TAG_START_OPEN GENERIC_ID (attribute)* TAG_CLOSE ;

attribute  : GENERIC_ID ATTR_EQ ATTR_VALUE ;

endTag :  TAG_END_OPEN GENERIC_ID TAG_CLOSE ;

emptyElement : TAG_START_OPEN GENERIC_ID  (attribute)* TAG_EMPTY_CLOSE ;
