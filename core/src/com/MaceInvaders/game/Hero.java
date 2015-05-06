package com.MaceInvaders.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hero
{
  Sprite sprite;
  Texture texture;
  boolean alive = true;
  public int livesLeft = 3;

  
  public Hero()
  {
    texture = new Texture("NewImage.png");
    sprite = new Sprite(texture);
    sprite.setCenterY(GameData.HERO_START_Y);
    sprite.setCenterX(GameData.HERO_START_X);
  }

  public void update(float dx)
  {
    sprite.translateX(dx);
  }
  
  public void draw(SpriteBatch batch)
  {
    sprite.draw(batch);
  }
  
  public Arrow fire()
  {
    return new Arrow(sprite.getX() + sprite.getWidth()/2,sprite.getY() + sprite.getHeight());
  }
}
