import { Comment } from './comment.model';

export interface Video {
  id?: number;
  title: string;
  description: string;
  date?: Date;
  publisherId: number;
  thumbnailUrl?: string;
  videoUrl?: string;
  comments?: Array<Comment>;
  likes?: Array<number>;
}
