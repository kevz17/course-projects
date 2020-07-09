#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "a4.h"

#define PRINT_DEBUG 1

// Creates a card
Card* CreateCard(Suit suit, Name name) {
  Card* card;
  int initial_value = -1;
  card = malloc(sizeof(Card));
  card->suit = suit;
  card->name = name;
  card->value = initial_value;
  return card;
}

// Frees a card
void DestroyCard(Card* card) {
  free(card);
}

// Creates a struct deck with two fields
Deck* CreateDeck() {
  Deck* deck;
  deck = malloc(sizeof(Deck));
  deck->top_card = -1;
  return deck;
}

// Frees all the memory allocated for the cards and deck
void DestroyDeck(Deck* deck) {
  for (int i = deck->top_card; i >= 0; i--) {
    DestroyCard(deck->cards[i]);
  }
  free(deck);
}

// Pushes a card to the deck
Deck* PushCardToDeck(Card* card, Deck* deck) {
  if (!(deck->top_card == kNumCardsInDeck - 1)) {
    deck->cards[++deck->top_card] = card;
    return deck;
  }
}

// Shows the top card
Card* PeekAtTopCard(Deck* deck) {
  if (!IsDeckEmpty(deck)) {
    return deck->cards[deck->top_card];
  }
  return NULL;
}

// Removes the top card
Card* PopCardFromDeck(Deck* deck) {
  if (!IsDeckEmpty(deck)) {
    return deck->cards[deck->top_card--];
  }
  return NULL;
}

// Checks if the deck is empty
int IsDeckEmpty(Deck* deck) {
  return deck->top_card == -1;
}

// Creates all the cards and pushes them into the Deck
Deck* PopulateDeck() {
  Deck *deck = CreateDeck();

  for (int i = 0; i <= 3; i++) {
    for (int j = 9; j <= 14; j++) {
      PushCardToDeck(CreateCard(i, j), deck);
    }
  }
  return deck;
}

// Helper function that swaps two values, used below
void Swap(Card* card1, Card* card2) {
  Card temp;
  temp = *card1;
  *card1 = *card2;
  *card2 = temp;
}

// Helper function used to shuffle a Card array randomly, used below
void Randomize(Card* cards[]) {
  // srand(time(0)); // Commented out as an alternative option
  unsigned int seed = time(0);
  for (int i = kNumCardsInDeck-1; i > 0; i--) {
    // int j = rand() % (i+1); // Used with srand commented above
    // Used rand_r to avoid clint.py style issue
    int j = rand_r(&seed) % (i+1);
    Swap(cards[i], cards[j]);
  }
}

// Takes all the cards in the deck, rearrange the order,
// and push the cards back into the Deck
void Shuffle(Deck* deck) {
  Card* new_deck[kNumCardsInDeck];
  for (int i = 0; i < kNumCardsInDeck; i++) {
    new_deck[i] = PopCardFromDeck(deck);
  }

  Randomize(new_deck);

  for (int i = 0; i < kNumCardsInDeck; i++) {
    PushCardToDeck(new_deck[i], deck);
  }
}
