package com.MaceInvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameWon implements Screen
{
  public GameWon()
  {
    // TODO Auto-generated constructor stub
  }
  SpriteBatch batch;
  Sprite gameWon;
  Sprite templar;
  Sprite background;
  public float time = 0.0f;
  

  
  MaceInvadersGame game;
  
  public GameWon(MaceInvadersGame game)
  {
    this.game = game;
    
    batch = new SpriteBatch();

    background = new Sprite(new Texture("background.jpg"));
    gameWon = new Sprite(new Texture("won.png"));
    templar = new Sprite(new Texture("templar.png"));
    
    gameWon.setCenter(640, 600);
    templar.setCenter(640, 250);
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
    time +=delta;
    background.draw(batch);
    gameWon.draw(batch);
    templar.draw(batch);
    if(time > 2.0f)
    {
      checkMenuTouch();
    }
    batch.end();
    
    
  }

  public void checkMenuTouch()
  {
    if(Gdx.input.isTouched())
    {
       game.setNewScreen(0);
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
    // TODO Auto-generated method stub
    
  }

  @Override
  public void dispose()
  {
    // TODO Auto-generated method stub
    
  }
}
