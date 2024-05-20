import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { EmailTemplateComponent } from './email-template.component';
import { EmailTemplateDetailComponent } from './email-template-detail.component';
import { EmailTemplateUpdateComponent } from './email-template-update.component';
import { EmailTemplateDeletePopupComponent, EmailTemplateDeleteDialogComponent } from './email-template-delete-dialog.component';
import { emailTemplateRoute, emailTemplatePopupRoute } from './email-template.route';

const ENTITY_STATES = [...emailTemplateRoute, ...emailTemplatePopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EmailTemplateComponent,
    EmailTemplateDetailComponent,
    EmailTemplateUpdateComponent,
    EmailTemplateDeleteDialogComponent,
    EmailTemplateDeletePopupComponent,
  ],
  entryComponents: [EmailTemplateDeleteDialogComponent],
})
export class IngestionApiEmailTemplateModule {}
