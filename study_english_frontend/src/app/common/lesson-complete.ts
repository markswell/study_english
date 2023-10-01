import { Audios } from "./audio";

export class LessonComplete {

    audios: Audios[];
    bookUrl: string;

    constructor(audios: Audios[], bookUrl: string){
        this.audios = audios;
        this.bookUrl = bookUrl;
    }

}