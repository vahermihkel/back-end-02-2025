import { Category } from "./Category"

export class Product {
  constructor(
    public name: string,
    public price: number,
    public image: string,
    public active: boolean,
    public category: Category,
    public id?: number) {}
}