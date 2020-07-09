#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

#include "a4.h"

#define kPrintDebug 1

// Returns a hand pointer to the newly created heap memory
// Note that it's the user's responsibility to free the
// allocated memory
Hand* CreateHand() {
  Hand* new_hand = malloc(sizeof(Hand));
  new_hand->num_cards_in_hand = 0;
  new_hand->first_card = NULL;
  return new_hand;
}

// Returns a CardNode pointer to the newly created heap memory
// Note that it's the user's responsibility to free the
// allocated memory
CardNode* CreateCardNode(Card* card) {
  CardNode* new_card_node = malloc(sizeof(CardNode));
  new_card_node->this_card = card;
  new_card_node->prev_card = NULL;
  new_card_node->next_card = NULL;
  return new_card_node;
}

// Adds a card to a hand
void AddCardToHand(Card* card, Hand* hand) {
  if (hand->num_cards_in_hand < kNumCardsInHand) {
    CardNode* new_card_node = CreateCardNode(card);
    if (hand->first_card != NULL) {
      new_card_node->next_card = hand->first_card;
      hand->first_card->prev_card = new_card_node;
      new_card_node->prev_card = NULL;
    }
    hand->first_card = new_card_node;
    hand->num_cards_in_hand++;
  }
}

// Helper function to remove the given card node
CardNode* RemoveCardNode(CardNode* node, Hand* hand) {
  if (hand->first_card == node) {
    hand->first_card = NULL;
    hand->first_card = node->next_card;
  }
  if (node->prev_card != NULL) {
    node->prev_card->next_card = node->next_card;
  }
  if (node->next_card != NULL) {
    node->next_card->prev_card = node->prev_card;
  }
  return node;
}

// Helper function to destroy the given card node
void DestroyCardNode(CardNode* node) {
  node->next_card = NULL;
  node->prev_card = NULL;
  node->this_card = NULL;
  free(node);
}

// Removes given card from hand
Card* RemoveCardFromHand(Card* card, Hand* hand) {
  CardNode* current_card_node = hand->first_card;

  while (current_card_node != NULL) {
    if (current_card_node->this_card == card) {
      hand->num_cards_in_hand--;
      CardNode* card_node = RemoveCardNode(current_card_node, hand);
      Card* card = card_node->this_card;
      DestroyCardNode(card_node);
      return card;
    }
    current_card_node = current_card_node->next_card;
  }
  return NULL;
}

// Checks whether a hand has no cards
int IsHandEmpty(Hand* hand) {
  if (hand->num_cards_in_hand == 0) {
    return 1;
  }
  return 0;
}

// Destroy the cards in hand then destroy hand
void DestroyHand(Hand* hand) {
  while (hand->first_card != NULL) {
    CardNode* deleted_node = RemoveCardNode(hand->first_card, hand);
    DestroyCardNode(deleted_node);
  }
  free(hand);
}

// Shuffle has been modified for Milestone 2, and is still in deck.c
// void Shuffle(Deck *deck) {
// }

// Deals cards one by one to each hand
void Deal(Deck *deck, Hand *p1hand, Hand *p2hand) {
  for (int i = 0; i < kNumCardsInHand; i++) {
    AddCardToHand(PopCardFromDeck(deck), p1hand);
    AddCardToHand(PopCardFromDeck(deck), p2hand);
  }
}

// Helper function that checks whether the given hand has the given suit
int HasGivenSuit(Hand* hand, Card* card) {
  CardNode* head = hand->first_card;
  while (head != NULL) {
    if (head->this_card->suit == card->suit) {
      return 1;
    }
    head = head->next_card;
  }
  return 0;
}

// Checks whether the move is legal according to the rules
int IsLegalMove(Hand *hand, Card *lead_card, Card *played_card) {
  if (!HasGivenSuit(hand, lead_card)) {
    return 1;
  }
  return (lead_card->suit == played_card->suit);
}

// Returns the winner of the round as 1 for leader, 0 for follower
int WhoWon(Card* lead_card, Card* followed_card, Suit trump) {
  if (lead_card->suit == followed_card->suit) {
    return (lead_card->name > followed_card->name);
  }
  if (followed_card->suit == trump) {
    return 0;
  }
  return 1;
}

// Returns cards from hands to deck
void ReturnHandToDeck(Hand* hand, Deck* deck) {
  if (!IsHandEmpty(hand)) {
    PushCardToDeck(RemoveCardFromHand(hand->first_card->this_card, hand), deck);
  }
}
