import { Comment } from './comment.model';

export interface Video {
  id?: number;
  title: string;
  description: string;
  date?: Date;
  publisherId: number;
  thumbnail?: File;
  data?: File;
  comments?: Array<Comment>;
  likes?: Array<number>;
}
