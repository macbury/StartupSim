package de.macbury.startup.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by macbury on 26.09.16.
 */
public class BuildAtlases {
  public static void main (String[] arg) {
    TexturePacker.Settings settings = new TexturePacker.Settings();
    settings.grid = true;
    settings.square = true;
    settings.paddingX = 2;
    settings.paddingY = 2;

    TexturePacker.process("../../raw/interior", "atlases", "interior.atlas");
    TexturePacker.process("../../raw/notifications", "atlases", "notifications.atlas");
  }
}
