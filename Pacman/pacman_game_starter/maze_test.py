from maze import Maze
from game_controller import GameController


def test_constructor():
    g = GameController(600, 400)
    m = Maze(600, 400, 150, 450,
             100, 300, g)
    assert m.LEFT_VERT == 150
    assert m.RIGHT_VERT == 450
    assert m.TOP_HORIZ == 100
    assert m.BOTTOM_HORIZ == 300
    assert m.WIDTH == 600
    assert m.HEIGHT == 400
    assert m.gc is g
    assert m.dots.dots_left() == ((m.dots.WIDTH//m.dots.SPACING + 1) * 2 +
                                  (m.dots.HEIGHT//m.dots.SPACING + 1) * 2)


def test_eat_dots():
    g = GameController(600, 400)
    m = Maze(600, 400, 150, 450,
             100, 300, g)
    assert m.eat_dots(0, m.TOP_HORIZ)[0][0].x == m.dots.SPACING
    assert m.dots.top_row[-1].x == m.dots.WIDTH - m.dots.SPACING
    assert len(m.eat_dots(m.LEFT_VERT - m.dots.EAT_DIST, m.BOTTOM_HORIZ)[1])\
        == m.dots.WIDTH//m.dots.SPACING
    assert len(m.dots.left_col) == m.dots.HEIGHT//m.dots.SPACING
