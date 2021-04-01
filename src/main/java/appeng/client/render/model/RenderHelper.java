/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.client.render.model;

import java.util.EnumMap;
import java.util.List;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

// TODO: Investigate use of CubeBuilder instead
final class RenderHelper {

    private static EnumMap<Direction, List<Vector3f>> cornersForFacing = generateCornersForFacings();

    private RenderHelper() {

    }

    static List<Vector3f> getFaceCorners(Direction side) {
        return cornersForFacing.get(side);
    }

    private static EnumMap<Direction, List<Vector3f>> generateCornersForFacings() {
        EnumMap<Direction, List<Vector3f>> result = new EnumMap<>(Direction.class);

        for (Direction facing : Direction.values()) {
            List<Vector3f> corners;

            float offset = (facing.getAxisDirection() == Direction.AxisDirection.field_11060) ? 0 : 1;

            switch (facing.getAxis()) {
                default:
                case field_11048:
                    corners = Lists.newArrayList(new Vector3f(offset, 1, 1), new Vector3f(offset, 0, 1),
                            new Vector3f(offset, 0, 0), new Vector3f(offset, 1, 0));
                    break;
                case field_11052:
                    corners = Lists.newArrayList(new Vector3f(1, offset, 1), new Vector3f(1, offset, 0),
                            new Vector3f(0, offset, 0), new Vector3f(0, offset, 1));
                    break;
                case field_11051:
                    corners = Lists.newArrayList(new Vector3f(0, 1, offset), new Vector3f(0, 0, offset),
                            new Vector3f(1, 0, offset), new Vector3f(1, 1, offset));
                    break;
            }

            if (facing.getAxisDirection() == Direction.AxisDirection.field_11060) {
                corners = Lists.reverse(corners);
            }

            result.put(facing, ImmutableList.copyOf(corners));
        }

        return result;
    }

    private static Vector3d adjust(Vector3d vec, Direction.Axis axis, double delta) {
        switch (axis) {
            default:
            case field_11048:
                return new Vector3d(vec.x + delta, vec.y, vec.z);
            case field_11052:
                return new Vector3d(vec.x, vec.y + delta, vec.z);
            case field_11051:
                return new Vector3d(vec.x, vec.y, vec.z + delta);
        }
    }
}
