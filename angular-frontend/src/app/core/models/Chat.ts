export interface Chat {
  id: string;               // Unique identifier for the chat
  title: string;            // Title of the chat
  participants: Participant[]; // List of participants in the chat
  lastMessage: Message;     // The last message exchanged in the chat
  updatedAt: Date;          // When the chat was last updated
}

export interface Participant {
  id: string;               // Unique identifier for the participant
  name: string;             // Display name of the participant
  avatarUrl?: string;       // URL to the participant's avatar image
}

export interface Message {
  id: string;               // Unique identifier for the message
  senderId: string;         // The ID of the participant who sent the message
  content: string;          // The text content of the message
  date: Date;          // When the message was sent
}
