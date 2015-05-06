package com.MaceInvaders.game;

import com.badlogic.gdx.graphics.Texture;

public class SimpleBadGuy extends GeneralVillain
{ 
  public SimpleBadGuy(float x, float y)
  {
    super(x,y, new Texture("Simple.png"));
    canShoot = false;
  }

}
