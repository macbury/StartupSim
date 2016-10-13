package de.macbury.tests.startupsim.core.map.quadtree;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import de.macbury.startup.map.quadtree.QuadTree;
import de.macbury.tests.startupsim.support.BaseGameTest;
import static org.junit.Assert.*;
import org.junit.Test;

public class QuatTreeTest extends BaseGameTest {
  @Test
  public void testInsertElements() {

    QuadTree.maxItemByNode = 1;
    QuadTree.maxLevel = 2;

    QuadTree<Rectangle> quadTree = new QuadTree<Rectangle>(0, 0, 10, 10);

    Rectangle r1 = new Rectangle(1, 1, 1, 1);
    Rectangle r2 = new Rectangle(2, 2, 1, 1);
    Rectangle r3 = new Rectangle(4, 4, 1, 1);
    Rectangle r4 = new Rectangle(6, 6, 1, 1);
    Rectangle r5 = new Rectangle(4, 4, 2, 2);
    Rectangle r6 = new Rectangle(0.5f, 6.5f, 0.5f, 0.5f);

    quadTree.insert(r1, r1);
    quadTree.insert(r2, r2);
    quadTree.insert(r3, r3);
    quadTree.insert(r4, r4);
    quadTree.insert(r5, r5);
    quadTree.insert(r6, r6);

    Array<Rectangle> list = new Array<Rectangle>();
    quadTree.getElements(list, new Rectangle(2, 2, 1, 1));

    Array<Rectangle> expected = new Array<Rectangle>();
    expected.add(r1);
    expected.add(r3);
    expected.add(r5);
    expected.add(r2);

    assertEquals(expected, list);

    list.clear();
    quadTree.getElements(list, new Rectangle(4, 2, 1, 1));

    expected.clear();
    expected.add(r1);
    expected.add(r3);
    expected.add(r2);
    expected.add(r5);

    Array<Rectangle> zoneList = new Array<Rectangle>();
    quadTree.getAllZones(zoneList);

    assertTrue(zoneList.size == 9);
  }
}
