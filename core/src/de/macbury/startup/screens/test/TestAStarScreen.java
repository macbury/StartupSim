package de.macbury.startup.screens.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.SmoothableGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import de.macbury.startup.map.MapData;
import de.macbury.startup.map.pfa.MapGraph;
import de.macbury.startup.map.Tile;
import de.macbury.startup.map.pfa.SmoothedGraphPath;
import de.macbury.startup.map.pfa.TileDistanceHeuristic;
import de.macbury.startup.map.pfa.TileNode;
import de.macbury.startup.screens.AbstractScreen;

/**
 * Created by macbury on 29.09.16.
 */
public class TestAStarScreen extends AbstractScreen implements GestureDetector.GestureListener {
  private MapData mapData;
  private OrthographicCamera camera;
  private FillViewport worldViewport;
  private ShapeRenderer shapeRenderer;
  private SmoothedGraphPath path;
  private MapGraph mapGraph;
  private IndexedAStarPathFinder<TileNode> pathFinder;

  @Override
  public void preload() {

  }

  @Override
  public void create() {
    this.mapData = new MapData(30,30);

    for (int x = 1; x < 29; x++) {
      for (int y = 1; y < 29; y++) {
        mapData.build(x, y);
      }
    }


    for (int i = 0; i < 20; i++) {
      mapData.remove(i,4);
    }


    this.mapGraph = new MapGraph(mapData);
    mapGraph.rebuild();

    this.path = new SmoothedGraphPath();

    this.pathFinder = new IndexedAStarPathFinder<TileNode>(mapGraph, true);


    this.camera       = new OrthographicCamera();
    worldViewport     = new FillViewport(64, 64, camera);

    shapeRenderer     = new ShapeRenderer();
  }

  @Override
  public void resize(int width, int height) {
    worldViewport.update(width,height);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(new GestureDetector(this));
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
    camera.update();
    shapeRenderer.setProjectionMatrix(camera.combined);

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line); {
      for (int x = 0; x < mapData.getColumns(); x++) {
        for (int y = 0; y < mapData.getRows(); y++) {
          if (mapData.isEmpty(x,y)) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(x,y, 1, 1);
          }
        }
      }

      for (int i = 1; i < path.getCount(); i++) {
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

      shapeRenderer.setColor(Color.YELLOW);
      shapeRenderer.rect(touchPos.x, touchPos.y, 1,1);
    } shapeRenderer.end();
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
    findPath((int)touchPos.x, (int)touchPos.y);

    return false;
  }

  private void findPath(int x, int y) {
    path.clear();
    pathFinder.searchNodePath(
            mapGraph.getNode(x, y),
            mapGraph.getNode(1, 1),
            new TileDistanceHeuristic(),
            path
    );
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
    camera.position.sub(deltaX/100,-deltaY/100, 0);
    camera.update();
    return true;
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
