package model;

/**
 * A bill class modeling a bill into a order.
 *
 * @param id    Bill id.
 * @param price Total price of the order.
 */
public record Bill(long id, double price) {
}