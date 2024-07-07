from training.models.Context import Context
from training.service.Service import Service

class ReportingService(Service):

    def do_service(self, context: Context):
        self.log_emails(context)
        self.process_emails(context)
        self.reject_emails(context)

    def log_emails(self, context: Context):
        print(f"Emails to be processed: {context.emails_to_be_processed}")
        print(f"Emails to be rejected: {context.emails_to_be_rejected}")

    def process_emails(self, context: Context):
        self.update_email_state(context, context.emails_to_be_processed, 'PROCESSED')

    def reject_emails(self, context: Context):
        self.update_email_state(context, context.emails_to_be_rejected, 'REJECTED')

    def update_email_state(self, context: Context, emails, state: str):
        for email in emails:
            payload = {
                'user': {'email': email},
                'state': state
            }

            context.orchestration_service_api_client.put(endpoint='api/product/user', json=payload)
