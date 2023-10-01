import { Lesson } from "./lesson";

export interface Book {
    id: number;
    book: string;
    lessons: Lesson[];
}