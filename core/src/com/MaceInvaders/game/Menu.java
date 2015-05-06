package com.MaceInvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu implements Screen
{
  SpriteBatch batch;
  Sprite menuBackground;
  Sprite menuButton;
  Sprite background;
  
  Texture backgroundTexture;
  Texture menuBackgroundTexture;
  Texture menuButtonTexture;
  
  MaceInvadersGame game;
  
  public Menu(MaceInvadersGame game)
  {
    this.game = game;
    
    batch = new SpriteBatch();
    
    menuBackgroundTexture = new Texture("MenuText.png");
    menuButtonTexture = new Texture("NewGame2.png");
    backgroundTexture = new Texture("background.jpg");
    
    background = new Sprite(backgroundTexture);
    menuBackground = new Sprite(menuBackgroundTexture);
    menuButton = new Sprite(menuButtonTexture);
    menuBackground.setCenter(640, 650);
    menuButton.setCenter(640, 350);
  }

  @Override
  public void show()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void render(float delta)
  {
    batch.begin();
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    background.draw(batch);
    menuBackground.draw(batch);
    menuButton.draw(batch);
    checkMenuTouch();
    batch.end();
    
  }

  public void checkMenuTouch()
  {
    if(menuButton.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.input.getY()))
    {
       game.setNewScreen(1);
    }
  }
  @Override
  public void resize(int width, int height)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void pause()
  {
    
    // TODO Auto-generated method stub
    
  }

  @Override
  public void resume()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void hide()
  {
    dispose();
  }

  @Override
  public void dispose()
  {
    //menuBackgroundTexture.dispose();
    //menuButtonTexture.dispose();
   // batch.dispose();

    
  }
}
