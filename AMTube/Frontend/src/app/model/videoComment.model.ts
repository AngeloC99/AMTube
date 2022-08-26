export interface VideoComment {
  id?: number;
  date?: Date;
  publisherId: number;
  publisherUsername: string;
  text: string;
  videoId: number;
}
