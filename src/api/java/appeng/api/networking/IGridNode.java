/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 AlgorithmX2
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package appeng.api.networking;

import java.util.EnumSet;

import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.IWorld;
import appeng.api.IAppEngApi;
import appeng.api.util.AEPartLocation;
import appeng.api.util.IReadOnlyCollection;

/**
 * Gives you a view into your Nodes connections and information.
 * <p>
 * updateState, getGrid, destroy are required to implement a proper IGridHost.
 * <p>
 * Don't Implement; Acquire from {@link IAppEngApi}.createGridNode
 */
public interface IGridNode {

    /**
     * lets you walk the grid stating at the current node using a IGridVisitor, generally not needed, please use only if
     * required.
     *
     * @param visitor visitor
     */
    void beginVisit(@Nonnull IGridVisitor visitor);

    /**
     * inform the node that your IGridBlock has changed its internal state, and force the node to update.
     * <p>
     * ALWAYS make sure that your block entity is in the world, and has its node properly saved to be returned from the
     * host before updating state,
     * <p>
     * If your entity is not in the world, or if you IGridHost returns a different node for the same side you will
     * likely crash the game.
     */
    void updateState();

    /**
     * get the machine represented by the node.
     *
     * @return grid host
     */
    @Nonnull
    IGridHost getMachine();

    /**
     * get the grid for the node, this can change at a moments notice.
     *
     * @return grid
     */
    @Nonnull
    IGrid getGrid();

    /**
     * By destroying your node, you destroy any connections, and its existence in the grid, use in invalidate, or
     * onChunkUnload
     */
    void destroy();

    /**
     * @return the world the node is located in
     */
    @Nonnull
    IWorld getWorld();

    /**
     * @return a set of the connected sides, INTERNAL represents an invisible connection
     */
    @Nonnull
    EnumSet<AEPartLocation> getConnectedSides();

    /**
     * lets you iterate a nodes connections
     *
     * @return grid connections
     */
    @Nonnull
    IReadOnlyCollection<IGridConnection> getConnections();

    /**
     * @return the IGridBlock for this node
     */
    @Nonnull
    IGridBlock getGridBlock();

    /**
     * Reflects the networks status, returns true only if the network is powered, and the network is not booting, this
     * also takes into account channels.
     *
     * @return true if is Network node active, and participating.
     */
    boolean isActive();

    /**
     * this should be called for each node you create, if you have a nodeData compound to load from, you can store all
     * your nods on a single compound using name.
     * <p>
     * Important: You must call this before updateState.
     *
     * @param name     nbt name
     * @param nodeData to be loaded data
     */
    void loadFromNBT(@Nonnull String name, @Nonnull CompoundNBT nodeData);

    /**
     * this should be called for each node you maintain, you can save all your nodes to the same tag with different
     * names, if you fail to complete the load / save procedure, network state may be lost between game load/saves.
     *
     * @param name     nbt name
     * @param nodeData to be saved data
     */
    void saveToNBT(@Nonnull String name, @Nonnull CompoundNBT nodeData);

    /**
     * @return if the node's channel requirements are currently met, use this for display purposes, use isActive for
     *         status.
     */
    boolean meetsChannelRequirements();

    /**
     * see if this node has a certain flag
     *
     * @param flag flags
     * @return true if has flag
     */
    boolean hasFlag(@Nonnull GridFlags flag);

    /**
     * @return the ownerID this represents the person who placed the node.
     */
    int getPlayerID();

    /**
     * tell the node who was responsible for placing it, failure to do this may result in in-compatibility with the
     * security system. Called instead of loadFromNBT when initially placed, once set never required again, the value is
     * saved with the Node NBT.
     *
     * @param playerID new player id
     */
    void setPlayerID(int playerID);
}
