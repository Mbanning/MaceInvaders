package com.MaceInvaders.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeneralVillain
{
  Sprite sprite;
  Texture texture;
  boolean alive = true;
  boolean canShoot;
  
  public GeneralVillain(float x, float y, Texture texture)
  {
    this.texture = texture;
    sprite = new Sprite(texture);    
    sprite.setCenterY(y);
    sprite.setCenterX(x);
  }

  public void updateX(float dx)
  {
    sprite.translateX(dx);
  }
  public void updateY(float dy)
  {
    sprite.translateY(dy);
  }
  
  public void draw(SpriteBatch batch)
  {
    sprite.draw(batch);
  }
  
  public BadArrow fire()
  {
    return new BadArrow(sprite.getX() + sprite.getWidth()/2,sprite.getY());
  }
}
