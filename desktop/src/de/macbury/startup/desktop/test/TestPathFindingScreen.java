package de.macbury.startup.desktop.test;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import de.macbury.startup.entities.blueprint.EntityBlueprint;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.level.LevelEnv;
import de.macbury.startup.map.pfa.SmoothedGraphPath;
import de.macbury.startup.map.pfa.TileDistanceHeuristic;
import de.macbury.startup.map.pfa.TileNode;
import de.macbury.startup.map.quadtree.QuadTree;
import de.macbury.startup.messages.MessageType;
import de.macbury.startup.screens.AbstractScreen;
import io.piotrjastrzebski.bte.AIEditor;

/**
 * Created by macbury on 30.09.16.
 */
public class TestPathFindingScreen extends AbstractScreen implements GestureDetector.GestureListener {
  private LevelEnv level;
  private FillViewport worldViewport;
  private ShapeRenderer shapeRenderer;
  private Entity programmerEntity;
  private AIEditor aiEditor;
  private Stage stage;
  private QuadTree<Entity> tree;

  @Override
  public void preload() {
    VisUI.load();
    game.assets.load("entity:programmer.json", EntityBlueprint.class);
    game.assets.load("entity:sandwich.json", EntityBlueprint.class);
  }

  @Override
  public void create() {
    this.aiEditor     = new AIEditor(VisUI.getSkin());
    aiEditor.addDefaultTaskClasses();

    tree              = new QuadTree<Entity>(0, 0, 10, 10);
    stage             = new Stage(new ScreenViewport());

    this.level        = new LevelEnv(game);
    worldViewport     = new FillViewport(24, 24, level.camera);

    shapeRenderer     = new ShapeRenderer();

    programmerEntity  = level.entities.spawn("entity:programmer.json", 2,2);

    InputMultiplexer inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(stage);
    inputMultiplexer.addProcessor(new GestureDetector(this));
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void resize(int width, int height) {
    worldViewport.update(width,height);
  }

  @Override
  public void show() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    shapeRenderer.setProjectionMatrix(level.camera.combined);

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line); {
      for (int x = 0; x < level.mapData.getColumns(); x++) {
        for (int y = 0; y < level.mapData.getRows(); y++) {
          if (level.mapData.isEmpty(x,y)) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(x,y, 1, 1);
          }
        }
      }

      Array<TileNode> path = Components.Movement.get(programmerEntity).getPath();

      if (path != null) {
        for (int i = 1; i < path.size; i++) {
          TileNode startNode = path.get(i-1);
          TileNode targetNode = path.get(i);

          shapeRenderer.setColor(Color.FOREST);
          shapeRenderer.line(
                  startNode.x + 0.5f,
                  startNode.y + 0.5f,
                  targetNode.x + 0.5f,
                  targetNode.y + 0.5f
          );
        }
      }


      shapeRenderer.setColor(Color.YELLOW);
      shapeRenderer.rect(touchPos.x, touchPos.y, 1,1);
    } shapeRenderer.end();

    level.update(delta);
  }

  @Override
  public void hide() {

  }

  @Override
  public boolean isDisposedAfterHide() {
    return false;
  }

  @Override
  public void dispose() {
    level.dispose();
  }

  @Override
  public boolean touchDown(float x, float y, int pointer, int button) {
    return false;
  }

  Vector3 touchPos = new Vector3();
  @Override
  public boolean tap(float x, float y, int count, int button) {
    touchPos.set(x,y,0);
    worldViewport.unproject(touchPos);
    touchPos.set(MathUtils.floor(touchPos.x), MathUtils.floor(touchPos.y), 0);
    if (Input.Buttons.LEFT == button) {
      //Components.Target.get(programmerEntity).set((int)touchPos.x, (int)touchPos.y);
      level.entities.spawn("entity:sandwich.json", (int)touchPos.x, (int)touchPos.y);
    } else {
      level.mapData.remove((int)touchPos.x, (int)touchPos.y);
    }

    return true;
  }

  @Override
  public boolean longPress(float x, float y) {
    return false;
  }

  @Override
  public boolean fling(float velocityX, float velocityY, int button) {
    return false;
  }

  @Override
  public boolean pan(float x, float y, float deltaX, float deltaY) {
    level.camera.position.sub(deltaX/100,-deltaY/100, 0);
    level.camera.update();
    return false;
  }

  @Override
  public boolean panStop(float x, float y, int pointer, int button) {
    return false;
  }

  @Override
  public boolean zoom(float initialDistance, float distance) {
    return false;
  }

  @Override
  public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
    return false;
  }

  @Override
  public void pinchStop() {

  }
}
