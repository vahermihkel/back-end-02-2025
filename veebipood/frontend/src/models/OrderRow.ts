import { Product } from "./Product";

export class OrderRow {
  constructor(
      public product: Product,
      public quantity: number
  ) {}
}