import { Audios } from "./audio";

export interface Lesson {
    id: number;
    lesson: string;
    audios: Audios[];
}