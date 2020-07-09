import time
from tiles import Tiles


class Board:
    """
    Displays the board and necessary information.
    Handles interaction between players.
    """
    def __init__(self, WIDTH, HEIGHT, SIZE, ADD_HEIGHT, game_controller):
        self.WIDTH = WIDTH
        self.HEIGHT = HEIGHT
        self.SIZE = SIZE
        self.ADD_HEIGHT = ADD_HEIGHT
        self.SPACING_hori = self.WIDTH/self.SIZE
        self.SPACING_vert = self.HEIGHT/self.SIZE
        self.tiles = Tiles(WIDTH, HEIGHT, SIZE)
        self.gc = game_controller
        self.red_dot = []  # Stores latest placed tile's position
        self.str_your_turn = "Your turn"
        self.str_comp_turn = "Computer's turn"
        self.turn_str = [self.str_your_turn]  # Indicates whose turn
        self.DELAY_TIME = 1  # Time of delay in seconds

    def display(self):
        """
        Displays tiles, board and related game information.
        Checks the game status and handles the computer part.
        """
        self.tiles.display()
        self.tiles.find_legal_moves()

        # Switches to computer if there are no legal moves for black tile
        if self.tiles.black_turn and len(self.tiles.black_lm) == 0:
            self.tiles.black_turn = False
        self.check_computer()  # Checks if it's computer's turn
        self.update()  # Updates game status

        # Displays additional game information such as latest placed
        # tile indicator (a red dot), tile counts of each side, game
        # status and which side is playing.
        if self.tiles.black_turn:
            self.turn_str.pop()
            self.turn_str.append(self.str_your_turn)
        else:
            self.turn_str.pop()
            self.turn_str.append(self.str_comp_turn)
        self.draw_lines()
        self.draw_legal_moves()
        self.draw_red_dot()
        self.draw_info_board()
        self.draw_counts()

    def check_computer(self):
        """
        Selects a legal move based on an algorithm if it's computer's
        turn. Places a white tile in the legal move and flips the black
        tiles. Displays necessary information and switches side.
        """
        if self.tiles.black_turn is False\
                or (self.tiles.black_turn and len(self.tiles.black_lm) == 0):
            # If there is a legal move for computer/white tile
            if len(self.tiles.white_lm) != 0:
                time.sleep(self.DELAY_TIME)  # Sets a delay
                pos = self.pick_legal_move_ai()
                # Places a white tile, including flipping black tiles
                self.tiles.place_computer(pos[0], pos[1])
                self.red_dot.append((pos[0], pos[1]))
                self.turn_str.pop()
                self.turn_str.append(self.str_your_turn)
            # Switches to black if there are no legal moves for computer
            else:
                self.tiles.black_turn = True
        # Switches to computer if there are no legal moves for black
        elif self.tiles.black_turn and len(self.tiles.black_lm) == 0:
            self.tiles.black_turn = False

    def pick_legal_move_ai(self):
        """
        Picks a position from legal moves so that it can flipp
        the most of black tiles. Returns a position triple.
        """
        # Assigns white legal moves to a local variable
        white_lm = self.tiles.white_lm
        # Assigns the position tuples of black tiles to be flipped
        # to a local variable.
        black_to_flip = self.tiles.computer_flip_pos
        # Stores triples of white tile positions and count of black
        # tiles to be flipped.
        white_count = []
        num_list = []  # Stores the flipped count
        # Chooses a legal move which can flip the most of black tiles
        for white_pos in white_lm:
            for pos_row in black_to_flip:
                if len(pos_row) != 0\
                        and (white_pos[0], white_pos[1])\
                        == pos_row[-1]:
                    pos_row.remove((white_pos[0], white_pos[1]))
                    white_count.append([white_pos[0], white_pos[1],
                                        len(pos_row)])
        for i in range(len(white_count)):
            num_list.append(white_count[i][-1])
        num_max = max(num_list)
        ind = num_list.index(num_max)
        pos = white_count[ind]
        return pos

    def update(self):
        """
        Checks if board is full or there is a legal move
        on board, then makes corresponding changes.
        """
        # Assigns counts of each side to game_controller's attributes
        self.gc.counts[0] = self.tiles.black_count
        self.gc.counts[1] = self.tiles.tiles_count - self.tiles.black_count

        if self.tiles.tiles_count == self.SIZE**2\
                or self.tiles.have_legal_move() is False:
            if self.tiles.black_count == self.tiles.tiles_count / 2:
                self.gc.ties = True
            elif self.tiles.black_count > self.tiles.tiles_count / 2:
                self.gc.player_wins = True
            else:
                self.gc.computer_wins = True

    def place_tiles(self, x, y):
        """Tries to place black tiles based on the positions of mouse"""
        # Calculates the rows and columns of the positions when mouse pressed
        row = int(y*self.SIZE // self.HEIGHT)
        column = int(x*self.SIZE // self.WIDTH)

        # Places tiles in the calculated row and column of the board
        # if it's a legal move.
        if (row, column) in self.tiles.black_lm:
            self.tiles.place(row, column)

            # Displays necessary information on board and makes changes
            # to related attributes.
            if len(self.red_dot) == 0:
                self.red_dot.append((row, column))
            self.red_dot.append((row, column))
            self.red_dot.pop(0)
            self.turn_str.pop()
            self.turn_str.append(self.str_comp_turn)
            self.draw_counts()

    def draw_legal_moves(self):
        """Draws the legal moves on board"""
        PERCENTAGE = 0.8  # Size of a legal move circle to a spot block
        STROKE_COLOR = 0  # Black
        STROKE_WEIGHT = 1
        if self.tiles.black_turn:
            if len(self.tiles.black_lm) != 0:
                for pos in self.tiles.black_lm:
                    stroke(STROKE_COLOR)
                    strokeWeight(STROKE_WEIGHT)
                    noFill()
                    ellipse(pos[1]*self.SPACING_hori + self.SPACING_hori/2,
                            pos[0]*self.SPACING_vert + self.SPACING_vert/2,
                            self.SPACING_hori * PERCENTAGE,
                            self.SPACING_vert * PERCENTAGE)

    def draw_red_dot(self):
        """Draws a red dot in the center of the latest placed tile"""
        PERCENTAGE = 0.1  # Size of a red dot to a spot block
        FILL_COLOR = (255, 0, 0)  # Red
        if len(self.red_dot) != 0:
            noStroke()
            fill(*FILL_COLOR)
            ellipse(
                self.red_dot[-1][1]*self.SPACING_hori + self.SPACING_hori/2,
                self.red_dot[-1][0]*self.SPACING_vert + self.SPACING_vert/2,
                self.SPACING_hori * PERCENTAGE,
                self.SPACING_vert * PERCENTAGE)

    def draw_info_board(self):
        """
        Displays information regarding count of tiles,
        whose turn and current game status.
        """
        TEXT_PERC = 0.2  # Size of text to a spot block
        VERTICAL_OFFSET = 0.55  # Offsets vertical position of text
        FILL_COLOR = 0.2  # Gray
        noStroke()
        rectMode(CENTER)
        fill(FILL_COLOR)
        rect(self.WIDTH/2, self.HEIGHT+self.ADD_HEIGHT/2,
             self.SPACING_hori*2, self.ADD_HEIGHT/2)

        textSize(self.ADD_HEIGHT * TEXT_PERC)
        textAlign(CENTER)
        fill(1)
        text(self.turn_str[-1], self.WIDTH/2,
             self.HEIGHT+self.ADD_HEIGHT*VERTICAL_OFFSET)

    def draw_counts(self):
        """Draws black and white tiles and their counts"""
        PERCENTAGE = 0.6  # Size of a tile to a spot block
        TEXT_PERC = 0.2  # Size of text to a spot block
        VERTICAL_OFFSET = 0.55  # Offsets vertical position of text
        STROKE_COLOR = 0  # Black
        STROKE_WEIGHT = 1
        FILL_COLOR_BL = 0  # Black
        FILL_COLOR_WH = 1  # White
        x1 = self.SPACING_hori  # Horizontal coordinate
        x2 = self.SIZE*self.SPACING_hori - self.SPACING_hori
        y = self.HEIGHT + self.ADD_HEIGHT/2  # Vertical coordinate
        tile_width = self.SPACING_hori * PERCENTAGE
        tile_height = self.SPACING_vert * PERCENTAGE

        # Draws black and white tiles
        stroke(STROKE_COLOR)
        strokeWeight(STROKE_WEIGHT)
        fill(FILL_COLOR_BL)
        ellipse(x1, y, tile_width, tile_height)
        fill(FILL_COLOR_WH)
        ellipse(x2, y, tile_width, tile_height)

        # Displays counts of black and white tiles
        textSize(self.ADD_HEIGHT * TEXT_PERC)
        textAlign(CENTER)
        fill(FILL_COLOR_WH)
        text(self.tiles.black_count, x1,
             self.HEIGHT+self.ADD_HEIGHT*VERTICAL_OFFSET)
        fill(FILL_COLOR_BL)
        text(self.tiles.tiles_count-self.tiles.black_count, x2,
             self.HEIGHT+self.ADD_HEIGHT*VERTICAL_OFFSET)

    def draw_lines(self):
        """Draws lines of board"""
        STROKE_COLOR = 0  # Black
        STROKE_WEIGHT = 1
        # Sets up characters of lines
        stroke(STROKE_COLOR)
        strokeWeight(STROKE_WEIGHT)
        # Draws horizontal lines
        for i in range(1, self.SPACING_vert):
            line(0, i*self.SPACING_vert, self.WIDTH, i*self.SPACING_vert)
        # Draws vertical lines
        for i in range(1, self.SPACING_hori):
            line(i*self.SPACING_hori, 0, i*self.SPACING_hori, self.HEIGHT)
