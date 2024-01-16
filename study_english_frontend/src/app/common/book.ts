import { Lesson } from "./lesson";

export interface Book {
    id: string;
    book: string;
    pdf: string;
    lessons: Lesson[];
}