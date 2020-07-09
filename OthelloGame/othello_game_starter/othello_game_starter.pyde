# This is the driver of the othello game. It sets the gaming window and
# displays interactions and status of the game. Also, it will perform certain
# operations when mouse is pressed. There are 4 classes defined in total. The
# top layer is board. It displays everything appearing on board and handles
# the computer player. There is also a game_controller class, which handles
# the result of the game, working with board. These two classes are built on
# the class of tiles. It deals with tiles related operations, including
# initiating tiles, displaying tiles, finding legal moves, placing and
# flipping tiles. The last class is tile, which draws black and white tiles.
# The computer or A.I. part is implemented in the class of board.

# For the record, there are two abbreviations widely used in codes: pos is
# short for position and lm is short for legal moves. Magic numbers like 0s
# and 1s are used for better readability.


from board import Board
from game_controller import GameController

WIDTH = 600
HEIGHT = 600
SIZE = 8  # The size of board, representing SIZE by SIZE spots
ADD_HEIGHT = HEIGHT/SIZE  # The height of added infomation board

game_controller = GameController(WIDTH, HEIGHT, SIZE, ADD_HEIGHT)
board = Board(WIDTH, HEIGHT, SIZE, ADD_HEIGHT, game_controller)


def setup():
    """Sets up the interactive window"""
    size(WIDTH, HEIGHT + ADD_HEIGHT)
    colorMode(RGB, 1)


def draw():
    """Draws the board, displays tiles and updates game status"""
    BACKGROUND_COLOR_SPEC = (0, 0.7, 0.5)
    background(*BACKGROUND_COLOR_SPEC)
    board.display()
    game_controller.update()


def mousePressed():
    """Tries to place a black tile when mouse is pressed"""
    board.place_tiles(mouseX, mouseY)
    # Immediately updates board after a black tile is placed
    board.tiles.display()
    board.draw_red_dot()
    board.draw_info_board()
