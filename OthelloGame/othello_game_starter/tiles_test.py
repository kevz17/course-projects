from tiles import Tiles


def test_constructor():
    ts = Tiles(500, 600, 8)
    assert ts.WIDTH == 500
    assert ts.HEIGHT == 600
    assert ts.SIZE == 8
    for i in range(8):
        for j in range(8):
            assert not ts.tiles_list[i][j] is True
    assert ts.tiles_count == 4
    assert ts.black_count == 2
    assert ts.black_turn is True


def test_initiate_tiles():
    # If SIZE = 0, it would raise error because some attributes
    # use SIZE value as a dividend. So SIZE = 0 is not tested.
    ts = Tiles(600, 600, 2)
    ts.initiate_tiles()
    assert ts.tiles_list == [[ts.tl_tile, ts.tr_tile],
                             [ts.bl_tile, ts.br_tile]]
    assert ts.tiles_list[0][0].isblack is False
    assert ts.tiles_list[0][1].isblack is True
    assert ts.tiles_list[1][0].isblack is True
    assert ts.tiles_list[1][1].isblack is False

    ts = Tiles(600, 600, 4)
    ts.initiate_tiles()
    assert ts.tiles_list == [[None, None, None, None],
                             [None, ts.tl_tile, ts.tr_tile, None],
                             [None, ts.bl_tile, ts.br_tile, None],
                             [None, None, None, None]]
    assert ts.tiles_list[1][1].isblack is False
    assert ts.tiles_list[1][2].isblack is True
    assert ts.tiles_list[2][1].isblack is True
    assert ts.tiles_list[2][2].isblack is False


def test_find_legal_moves():
    ts = Tiles(600, 600, 4)
    ts.find_legal_moves()
    assert ts.black_lm == {(0, 1), (1, 0), (2, 3), (3, 2)}
    assert ts.white_lm == set()
    assert ts.white_to_flip == [[], [], [], [(2, 2), (3, 2)],
                                [], [], [], [(1, 1), (1, 0)],
                                [], [], [(1, 1), (0, 1)], [],
                                [], [], [(2, 2), (2, 3)], []]
    assert ts.black_to_flip == []


def test_have_legal_move():
    ts = Tiles(600, 600, 4)
    ts.find_tile_position()
    assert ts.have_legal_move() is True


def test_find_tile_position():
    ts = Tiles(600, 600, 4)
    ts.find_tile_position()
    assert ts.black_pos == {(1, 2), (2, 1)}
    assert ts.white_pos == {(1, 1), (2, 2)}


def test_calculate_lm():
    ts = Tiles(600, 600, 4)
    a_set = set()
    a_list = []
    ts.calculate_lm({(1, 2), (2, 1)}, {(1, 1), (2, 2)}, a_set, a_list)
    assert a_set == {(0, 1), (1, 0), (2, 3), (3, 2)}
    assert a_list == [[], [], [], [(2, 2), (3, 2)],
                      [], [], [], [(1, 1), (1, 0)],
                      [], [], [(1, 1), (0, 1)], [],
                      [], [], [(2, 2), (2, 3)], []]


def test_place():
    ts = Tiles(600, 600, 4)
    ts.find_legal_moves()
    ts.place(0, 0)
    assert ts.black_count == 3
    assert ts.tiles_count == 5
    assert ts.black_turn is False


def test_place_computer():
    ts = Tiles(600, 600, 4)
    ts.find_legal_moves()
    ts.place_computer(0, 0)
    assert ts.tiles_count == 5
    assert ts.black_turn is True


def test_flip():
    ts = Tiles(600, 600, 4)
    ts.find_legal_moves()
    ts.place(0, 1)
    assert ts.black_count == 4
