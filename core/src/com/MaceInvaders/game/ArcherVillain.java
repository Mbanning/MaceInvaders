package com.MaceInvaders.game;

import com.badlogic.gdx.graphics.Texture;

public class ArcherVillain extends GeneralVillain
{
  public ArcherVillain(float x, float y)
  {
    super(x,y, new Texture("ArrowKnight.png"));
    canShoot = true;
  }
}

