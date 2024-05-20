import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetRoleDetailsDetailComponent } from 'app/entities/dataset-role-details/dataset-role-details-detail.component';
import { DatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';

describe('Component Tests', () => {
  describe('DatasetRoleDetails Management Detail Component', () => {
    let comp: DatasetRoleDetailsDetailComponent;
    let fixture: ComponentFixture<DatasetRoleDetailsDetailComponent>;
    const route = { data: of({ datasetRoleDetails: new DatasetRoleDetails(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetRoleDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DatasetRoleDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetRoleDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datasetRoleDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
