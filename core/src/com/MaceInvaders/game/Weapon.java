package com.MaceInvaders.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Weapon
{
  Sprite sprite;
  boolean alive = true;
  
  public Weapon(float x, float y, Texture texture)
  {
    sprite = new Sprite(texture);
    sprite.setCenter(x, y);
  }
  
  public void update(float dy)
  {
    sprite.translateY(dy);
  }
  
  public void draw(SpriteBatch batch)
  {
    sprite.draw(batch);
  }
  
}
