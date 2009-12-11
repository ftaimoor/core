/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.openlayers.api.feature;

import java.util.List;

import org.wicketstuff.openlayers.IOpenLayersMap;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * @author Marin Mandradjiev (marinsm@hotmail.com)
 * 
 */
public class Polygon extends Feature {
	private static final long serialVersionUID = 2381878612322151640L;

	public Polygon(List<Coordinate> coordinates) {
		super();
		setCoordinates(coordinates);
	}

	public Polygon(List<Coordinate> coordinates, FeatureStyle featureStyle) {
		super(featureStyle);
		setCoordinates(coordinates);
	}

	public Polygon(List<Coordinate> coordinates, IOpenLayersMap map) {
		super(map);
		setCoordinates(coordinates);
	}

	public Polygon(List<Coordinate> coordinates, FeatureStyle featureStyle,
			IOpenLayersMap map) {
		super(featureStyle, map);
		setCoordinates(coordinates);
	}

	@Override
	protected String getType() {
		return "OpenLayers.Geometry.LinearRing";
	}
}
