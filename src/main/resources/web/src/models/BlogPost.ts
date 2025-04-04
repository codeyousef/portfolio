export interface BlogPost {
  id: string;
  title: string;
  summary: string;
  content: string;
  imageUrl: string;
  tags: string[];
  publishDate: string | null;
  createdAt: string;
  updatedAt: string;
  published: boolean;
  slug: string;
  authorName: string;
  readingTimeMinutes: number | null;
}

export function isPublished(post: BlogPost): boolean {
  return post.published && !!post.publishDate && new Date(post.publishDate) <= new Date();
}

export function isDraft(post: BlogPost): boolean {
  return !isPublished(post);
}

export function hasTag(post: BlogPost, tag: string): boolean {
  return post.tags.includes(tag.trim());
}

export function getReadingTime(post: BlogPost): number {
  if (post.readingTimeMinutes !== null) {
    return post.readingTimeMinutes;
  }
  
  const wordCount = post.content.split(/\s+/).length;
  const minutesRaw = wordCount / 200.0;
  
  // Round up to nearest minute, with a minimum of 1 minute
  return Math.max(1, Math.ceil(minutesRaw));
} 