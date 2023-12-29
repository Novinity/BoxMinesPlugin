**BoxMines** - the essential plugin for all box-mining and prison servers.

**Features**:

    Easy to use and understand
    Select areas to create mines using WorldEdit
    Edit the percentage of ores in each mine
    Add any block to each mine
    Automatically broadcast mine resets
    Automatically teleport players out of the mine to prevent suffocation

**Planned Features**:

    Support for creating mines without WorldEdit
    Customizing all messages
    Optimizing mine resetting

**Commands & Permissions**:

    /bm list
      - **Permission**: boxmines.list
      - Lists the names of all added mines
    /bm add <mineName>
      - **Permission**: boxmines.add
      - Adds a mine
    /bm remove <mineName>
      - **Permission**: boxmines.remove
      - Removes a mine
    /bm set <mineName> <block> <spawnPercentage>
      - **Permission**: boxmines.set
      - Sets the percentage of the mine in which a block will appear. If you wish to replace one, you must first unset the old one.
      - Example:
        - Set the mine to have 90% cobblestone: /bm set <mineName> cobblestone 90
    /bm unset <mineName> <block>
      - **Permission**: boxmines.unset
      - Remove a block from a mine entirely
      - Example:
        - Remove cobblestone from the mine: /bm unset <mineName> cobblestone
    /bm tp <mineName>
      - **Permission**: boxmines.tp
      - Teleports the player to a mine
    /bm regenerate <mineName>
      - **Permission**: boxmines.regenerate
      - Resets a mine
    /bm setannounceregen <mineName>
      - **Permission**: boxmines.setannounceregen
      - Toggle whether or not the mine will announce when it resets
    /bm setregentime <mineName> <time in seconds>
      - **Permission**: boxmines.setregentime
      - Sets the amount of time between automated resets on a mine.
      - Set to 0 to disable
    /bm reload
      - **Permission**: boxmines.reload
      - Reloads the plugin
    /bm
      - Lists all the commands


**To report any bugs, post them in the discussion thread on SpigotMC or create a new issue in GitHub.**