from game_controller import GameController


def test_constructor():
    g = GameController(500, 600, 8, 75)
    assert g.WIDTH == 500
    assert g.HEIGHT == 600
    assert g.SIZE == 8
    assert g.ADD_HEIGHT == 75
    assert g.player_wins is False
    assert g.computer_wins is False
    assert g.ties is False
    assert g.counts == [0, 0]
    assert g.loop == 0


def test_handle_prompt_window():
    g = GameController(500, 600, 8, 75)
    g.handle_prompt_window()
    assert g.loop == 1


def test_edit_txt():
    g = GameController(500, 600, 8, 75)
    g.edit_txt("Kobe Bryant")
    g.counts = [1, 0]
    g.edit_txt("Dirk Nowitzki")
    with open("scores.txt", "r") as f:
        contents = f.readlines()
    assert contents[0] == "Dirk Nowitzki 1\n"
    assert contents[1] == "Kobe Bryant 0\n"
