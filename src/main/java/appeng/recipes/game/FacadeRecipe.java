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

package appeng.recipes.game;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;

import appeng.core.definitions.AEParts;
import appeng.core.definitions.ItemDefinition;
import appeng.items.parts.FacadeItem;

public final class FacadeRecipe extends CustomRecipe {
    public static SimpleCraftingRecipeSerializer<FacadeRecipe> SERIALIZER = null;

    private final ItemDefinition<?> anchor = AEParts.CABLE_ANCHOR;
    private final FacadeItem facade;

    public FacadeRecipe(CraftingBookCategory category, FacadeItem facade) {
        super(category);
        this.facade = facade;
    }

    @Override
    public boolean matches(CraftingInput inv, Level level) {
        return !this.getOutput(inv, false).isEmpty();
    }

    private ItemStack getOutput(CraftingInput inv, boolean createFacade) {
        if (inv.getItem(0).isEmpty() && inv.getItem(2).isEmpty() && inv.getItem(6).isEmpty()
                && inv.getItem(8).isEmpty()) {
            if (this.anchor.isSameAs(inv.getItem(1)) && this.anchor.isSameAs(inv.getItem(3))
                    && this.anchor.isSameAs(inv.getItem(5)) && this.anchor.isSameAs(inv.getItem(7))) {
                final ItemStack facades = this.facade.createFacadeForItem(inv.getItem(4), !createFacade);
                if (!facades.isEmpty() && createFacade) {
                    facades.setCount(4);
                }
                return facades;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider registryAccess) {
        return this.getOutput(inv, true);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<FacadeRecipe> getSerializer() {
        return getSerializer(facade);
    }

    public static RecipeSerializer<FacadeRecipe> getSerializer(FacadeItem facade) {
        if (SERIALIZER == null) {
            SERIALIZER = new SimpleCraftingRecipeSerializer<>((category) -> new FacadeRecipe(category, facade));
        }
        return SERIALIZER;
    }

}
