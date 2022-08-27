import { VideoComment } from './videoComment.model';

export interface Video {
  id?: number;
  title: string;
  description: string;
  date?: Date;
  publisherId: number;
  thumbnailUrl?: string;
  videoUrl?: string;
  comments?: Array<VideoComment>;
  likes?: Array<number>;
}
