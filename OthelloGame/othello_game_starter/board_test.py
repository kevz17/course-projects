from board import Board
from game_controller import GameController


def test_constructor():
    g = GameController(600, 600, 8, 75)
    b = Board(600, 600, 8, 75, g)
    assert b.WIDTH == 600
    assert b.HEIGHT == 600
    assert b.SIZE == 8
    assert b.ADD_HEIGHT == 75
    assert b.SPACING_hori == b.WIDTH/b.SIZE
    assert b.SPACING_vert == b.HEIGHT/b.SIZE
    assert b.tiles.black_turn is True
    assert b.gc is g
    assert b.red_dot == []
    assert b.str_your_turn == "Your turn"
    assert b.str_comp_turn == "Computer's turn"
    assert b.turn_str == [b.str_your_turn]
    assert b.DELAY_TIME == 1


def test_check_computer():
    g = GameController(600, 600, 8, 75)
    b = Board(600, 600, 8, 75, g)
    b.tiles.initiate_tiles()
    b.tiles.black_turn = False
    b.tiles.find_legal_moves()
    b.check_computer()
    assert b.red_dot[-1] == (4, 2)
    assert b.turn_str[-1] == b.str_your_turn


def test_pick_legal_move_ai():
    g = GameController(600, 600, 8, 75)
    b = Board(600, 600, 8, 75, g)
    b.tiles.initiate_tiles()
    b.tiles.black_turn = False
    b.tiles.find_legal_moves()
    assert b.pick_legal_move_ai() == [4, 2, 1]


def test_update():
    g = GameController(600, 600, 8, 75)
    b = Board(600, 600, 8, 75, g)
    b.tiles.tiles_count = 64
    b.tiles.black_count = 32
    b.update()
    assert b.gc.ties is True
    b.tiles.tiles_count = 64
    b.tiles.black_count = 33
    b.update()
    assert b.gc.player_wins is True
    b.tiles.tiles_count = 64
    b.tiles.black_count = 31
    b.update()
    assert b.gc.computer_wins is True


def test_place_tiles():
    g = GameController(600, 600, 8, 75)
    b = Board(600, 600, 8, 75, g)
    b.tiles.initiate_tiles()
    b.tiles.find_legal_moves()
    b.place_tiles(60, 550)
    assert b.tiles.tiles_list[7][0] is None
    # Since the place_tiles() function includes drawing functions linked to
    # Processing, it would raise NameError. So the test of placing a black
    # tile in a legal move area is not tested.
