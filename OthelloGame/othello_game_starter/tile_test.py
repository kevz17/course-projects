from tile import Tile


def test_constructor():
    t = Tile(1, 5, 500, 600, 8)
    assert t.PERCENTAGE == 0.85
    assert t.SPACING == 500/8
    assert t.x == 5*(500/8) + (500/8)/2
    assert t.y == 1*(500/8) + (500/8)/2
    assert t.tile_width == 500/8 * 0.85
    assert t.tile_height == 600/8 * 0.85
    assert t.isblack is True
