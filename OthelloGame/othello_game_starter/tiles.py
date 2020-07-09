from tile import Tile


class Tiles:
    """A collection of tiles and performs operations on tiles"""
    def __init__(self, WIDTH, HEIGHT, SIZE):
        self.WIDTH = WIDTH
        self.HEIGHT = HEIGHT
        self.SIZE = SIZE
        # Initiates a list of lists representing all the spots on board
        self.tiles_list = [[None for i in range(self.SIZE)]
                           for i in range(self.SIZE)]
        # Initiates the original 4 tiles
        self.initiate_tiles()
        # Initiates the counters for numbers of total tiles and black tiles
        self.tiles_count = 4
        self.black_count = 2
        # Sets the initial turn as black's
        self.black_turn = True

    def initiate_tiles(self):
        """
        Initiates the original 4 tiles: top left, top right, bottom left,
        bottom right and places them into corresponding positions of the list.
        """
        self.tl_tile = Tile(self.SIZE/2 - 1, self.SIZE/2 - 1, self.WIDTH,
                            self.HEIGHT, self.SIZE, isblack=False)
        self.tr_tile = Tile(self.SIZE/2 - 1, self.SIZE/2, self.WIDTH,
                            self.HEIGHT, self.SIZE)
        self.bl_tile = Tile(self.SIZE/2, self.SIZE/2 - 1, self.WIDTH,
                            self.HEIGHT, self.SIZE)
        self.br_tile = Tile(self.SIZE/2, self.SIZE/2, self.WIDTH,
                            self.HEIGHT, self.SIZE, isblack=False)
        self.tiles_list[self.SIZE//2 - 1][self.SIZE//2 - 1] = self.tl_tile
        self.tiles_list[self.SIZE//2 - 1][self.SIZE//2] = self.tr_tile
        self.tiles_list[self.SIZE//2][self.SIZE//2 - 1] = self.bl_tile
        self.tiles_list[self.SIZE//2][self.SIZE//2] = self.br_tile

    def display(self):
        """Displays all the tiles on board"""
        for row in self.tiles_list:
            for tile in row:
                if tile:
                    tile.display()

    def find_legal_moves(self):
        """Calculates legal moves and positions of tiles to be flipped"""
        # Find positions of all current tiles on board
        self.find_tile_position()
        # Used to store current legal moves
        self.black_lm = set()
        self.white_lm = set()
        # Used to store current tiles to flip
        self.white_to_flip = []
        self.black_to_flip = []
        # Check each black tile's legal moves in 8 directions
        if self.black_turn:
            self.calculate_lm(self.black_pos, self.white_pos,
                              self.black_lm, self.white_to_flip)
            if len(self.black_lm) == 0:
                self.black_turn = False
        # Check each white tile's legal moves in 8 directions
        elif self.black_turn is False:
            self.calculate_lm(self.white_pos, self.black_pos,
                              self.white_lm, self.black_to_flip)
            if len(self.white_lm) == 0:
                self.black_turn = True
            # Stores the positions of black tiles to be flipped
            # based on each legal move of computer.
            white_lm = set()
            black_to_flip = []
            self.calculate_lm(self.white_pos, self.black_pos,
                              white_lm, black_to_flip)
            self.computer_flip_pos = black_to_flip

    def have_legal_move(self):
        """Check if there are legal moves on board and returns a boolean"""
        black_lm = set()
        white_lm = set()
        white_to_flip = []
        black_to_flip = []
        self.calculate_lm(self.black_pos, self.white_pos,
                          black_lm, white_to_flip)
        self.calculate_lm(self.white_pos, self.black_pos,
                          white_lm, black_to_flip)
        length_lm = len(black_lm) + len(white_lm)
        if length_lm != 0:
            return True
        else:
            return False

    def find_tile_position(self):
        """Extracts positions of black and white tiles on board"""
        self.black_pos = set()
        self.white_pos = set()
        for row in range(self.SIZE):
            for col in range(self.SIZE):
                if self.tiles_list[row][col]:
                    if self.tiles_list[row][col].isblack:
                        self.black_pos.add((row, col))
                    else:
                        self.white_pos.add((row, col))

    def calculate_lm(self, pos_to_find, pos_other, lm, to_flip):
        """
        Takes 4 arguments as input: positions of tiles to be found, positions
        of tiles of the other player, empty set to store positions and empty
        list to store positions of tiles to be flipped. Then find legal moves
        of one player in 8 directions, and tiles to be flipped.
        """
        # Used to traverse positions in each direction
        for pos in pos_to_find:
            num = [1 for i in range(8)]

            # Northwest
            to_flip0 = []  # Stores position tuples of tiles to be flipped
            # The condition before and sets the edge of positions of tiles
            while (pos[0]-num[0] >= 0 and pos[1]-num[0] >= 0)\
                    and ((pos[0]-num[0], pos[1]-num[0]) in pos_other):
                to_flip0.append((pos[0]-num[0], pos[1]-num[0]))
                num[0] += 1
            if num[0] != 1\
                    and (pos[0]-num[0] >= 0 and pos[1]-num[0] >= 0)\
                    and self.tiles_list[pos[0]-num[0]][pos[1]-num[0]] is None:
                lm.add((pos[0]-num[0], pos[1]-num[0]))
                to_flip0.append((pos[0]-num[0], pos[1]-num[0]))
            to_flip.append(to_flip0)

            # Southwest
            to_flip1 = []
            while (pos[0]+num[1] <= self.SIZE-1 and pos[1]-num[1] >= 0)\
                    and ((pos[0]+num[1], pos[1]-num[1]) in pos_other):
                to_flip1.append((pos[0]+num[1], pos[1]-num[1]))
                num[1] += 1
            if num[1] != 1\
                    and (pos[0]+num[1] <= self.SIZE-1 and pos[1]-num[1] >= 0)\
                    and self.tiles_list[pos[0]+num[1]][pos[1]-num[1]] is None:
                lm.add((pos[0]+num[1], pos[1]-num[1]))
                to_flip1.append((pos[0]+num[1], pos[1]-num[1]))
            to_flip.append(to_flip1)

            # North
            to_flip2 = []
            while pos[0]-num[2] >= 0\
                    and ((pos[0]-num[2], pos[1]) in pos_other):
                to_flip2.append((pos[0]-num[2], pos[1]))
                num[2] += 1
            if num[2] != 1\
                    and pos[0]-num[2] >= 0\
                    and self.tiles_list[pos[0]-num[2]][pos[1]] is None:
                lm.add((pos[0]-num[2], pos[1]))
                to_flip2.append((pos[0]-num[2], pos[1]))
            to_flip.append(to_flip2)

            # South
            to_flip3 = []
            while pos[0]+num[3] <= self.SIZE-1\
                    and ((pos[0]+num[3], pos[1]) in pos_other):
                to_flip3.append((pos[0]+num[3], pos[1]))
                num[3] += 1
            if num[3] != 1\
                    and pos[0]+num[3] <= self.SIZE-1\
                    and self.tiles_list[pos[0]+num[3]][pos[1]] is None:
                lm.add((pos[0]+num[3], pos[1]))
                to_flip3.append((pos[0]+num[3], pos[1]))
            to_flip.append(to_flip3)

            # Northeast
            to_flip4 = []
            while (pos[0]-num[4] >= 0 and pos[1]+num[4] <= self.SIZE-1)\
                    and ((pos[0]-num[4], pos[1]+num[4]) in pos_other):
                to_flip4.append((pos[0]-num[4], pos[1]+num[4]))
                num[4] += 1
            if num[4] != 1\
                    and (pos[0]-num[4] >= 0 and pos[1]+num[4] <= self.SIZE-1)\
                    and self.tiles_list[pos[0]-num[4]][pos[1]+num[4]] is None:
                lm.add((pos[0]-num[4], pos[1]+num[4]))
                to_flip4.append((pos[0]-num[4], pos[1]+num[4]))
            to_flip.append(to_flip4)

            # Southeast
            to_flip5 = []
            while (pos[0]+num[5] <= self.SIZE-1
                   and pos[1]+num[5] <= self.SIZE-1)\
                    and ((pos[0]+num[5], pos[1]+num[5]) in pos_other):
                to_flip5.append((pos[0]+num[5], pos[1]+num[5]))
                num[5] += 1
            if num[5] != 1\
                    and (pos[0]+num[5] <= self.SIZE-1
                         and pos[1]+num[5] <= self.SIZE-1)\
                    and self.tiles_list[pos[0]+num[5]][pos[1]+num[5]] is None:
                lm.add((pos[0]+num[5], pos[1]+num[5]))
                to_flip5.append((pos[0]+num[5], pos[1]+num[5]))
            to_flip.append(to_flip5)

            # East
            to_flip6 = []
            while pos[1]+num[6] <= self.SIZE-1\
                    and ((pos[0], pos[1]+num[6]) in pos_other):
                to_flip6.append((pos[0], pos[1]+num[6]))
                num[6] += 1
            if num[6] != 1\
                    and pos[1]+num[6] <= self.SIZE-1\
                    and self.tiles_list[pos[0]][pos[1]+num[6]] is None:
                lm.add((pos[0], pos[1]+num[6]))
                to_flip6.append((pos[0], pos[1]+num[6]))
            to_flip.append(to_flip6)

            # West
            to_flip7 = []
            while pos[1]-num[7] >= 0\
                    and ((pos[0], pos[1]-num[7]) in pos_other):
                to_flip7.append((pos[0], pos[1]-num[7]))
                num[7] += 1
            if num[7] != 1\
                    and pos[1]-num[7] >= 0\
                    and self.tiles_list[pos[0]][pos[1]-num[7]] is None:
                lm.add((pos[0], pos[1]-num[7]))
                to_flip7.append((pos[0], pos[1]-num[7]))
            to_flip.append(to_flip7)

    def place(self, row, column):
        """
        Takes row and column of a position as inputs, places a black
        tile in the corresponding spot, increments counts and flips
        white tiles. Then switches to the computer player.
        """
        self.tiles_list[row][column] = Tile(
            row, column, self.WIDTH, self.HEIGHT, self.SIZE)
        self.black_count += 1
        self.tiles_count += 1
        self.flip(row, column, self.white_to_flip)
        self.black_turn = False

    def place_computer(self, row, column):
        """
        Takes row and column of a position as inputs, places a white
        tile in the corresponding spot, increments counts and flips
        black tiles. Then switches to human player.
        """
        self.tiles_list[row][column] = Tile(
            row, column, self.WIDTH, self.HEIGHT, self.SIZE, isblack=False)
        self.tiles_count += 1
        self.flip(row, column, self.black_to_flip)
        self.black_turn = True

    def flip(self, row, column, to_flip):
        """
        Takes row and column of a position and positions of tiles to be
        flipped as inputs, flips tiles and modifies the count of tiles.
        """
        for pos_row in to_flip:
            if len(pos_row) != 0 and (row, column) == pos_row[-1]:
                pos_row.remove((row, column))
                for pos in pos_row:
                    if self.black_turn:
                        self.tiles_list[pos[0]][pos[1]].isblack = True
                        self.black_count += 1
                    else:
                        self.tiles_list[pos[0]][pos[1]].isblack = False
                        self.black_count -= 1
