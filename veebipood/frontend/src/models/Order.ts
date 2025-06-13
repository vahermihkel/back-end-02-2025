import { OrderRow } from "./OrderRow";
import { Person } from "./Person";

export class Order {
  constructor(
      public created: Date,
      public totalSum: number,
      public orderRows: OrderRow[],
      public person: Person,
      public id?: number
  ) {}
}