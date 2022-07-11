import { Page } from "./Page";
import { Comment } from './Comment';

export class Post {
  id: string;
  text: string;
  link: string;
  linkPhoto: string;
  byUserId: string;
  byUsername: string;
  createdAt: Date;
  updatedAt: Date;
  totalComments: number;
  comments: Comment[];
}